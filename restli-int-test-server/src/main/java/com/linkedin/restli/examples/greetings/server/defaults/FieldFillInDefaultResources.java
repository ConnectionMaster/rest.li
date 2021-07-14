package com.linkedin.restli.examples.greetings.server.defaults;

import com.linkedin.restli.common.HttpStatus;
import com.linkedin.restli.examples.defaults.api.HighLevelRecordWithDefault;
import com.linkedin.restli.examples.defaults.api.LowLevelRecordWithDefault;
import com.linkedin.restli.examples.defaults.api.RecordCriteria;
import com.linkedin.restli.examples.greetings.api.Empty;
import com.linkedin.restli.examples.greetings.api.Greeting;
import com.linkedin.restli.server.ActionResult;
import com.linkedin.restli.server.BatchFinderResult;
import com.linkedin.restli.server.CollectionResult;
import com.linkedin.restli.server.PagingContext;
import com.linkedin.restli.server.annotations.Action;
import com.linkedin.restli.server.annotations.ActionParam;
import com.linkedin.restli.server.annotations.BatchFinder;
import com.linkedin.restli.server.annotations.Finder;
import com.linkedin.restli.server.annotations.PagingContextParam;
import com.linkedin.restli.server.annotations.QueryParam;
import com.linkedin.restli.server.annotations.RestLiCollection;
import com.linkedin.restli.server.annotations.RestMethod;
import com.linkedin.restli.server.resources.CollectionResourceTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestLiCollection(name = "fillInDefaults", namespace = "com.linkedin.restli.examples.defaults.api")
public class FieldFillInDefaultResources extends CollectionResourceTemplate<Long, HighLevelRecordWithDefault>
{
  @Override
  public HighLevelRecordWithDefault get(Long keyId)
  {
    return new HighLevelRecordWithDefault().setNoDefaultFieldA(Math.toIntExact(keyId));
  }

  @Override
  public Map<Long, HighLevelRecordWithDefault> batchGet(Set<Long> ids)
  {
    Map<Long, HighLevelRecordWithDefault> result = new HashMap<>();
    for (Long id : ids)
    {
      result.put(id, new HighLevelRecordWithDefault().setNoDefaultFieldA(Math.toIntExact(id)));
    }
    return result;
  }

  @RestMethod.GetAll
  public CollectionResult<HighLevelRecordWithDefault, LowLevelRecordWithDefault> getAllHighLevelRecordWithDefault(
      @PagingContextParam PagingContext pagingContext)
  {
    final int total = 3;
    List<HighLevelRecordWithDefault> elements = new LinkedList<>();
    for (int i = 0; i < total; i++)
    {
      elements.add(new HighLevelRecordWithDefault().setNoDefaultFieldA(i));
    }
    LowLevelRecordWithDefault metadata = new LowLevelRecordWithDefault();
    return new CollectionResult<>(elements, total, metadata);
  }

  @Finder("findRecords")
  public CollectionResult<HighLevelRecordWithDefault, LowLevelRecordWithDefault> findRecords(
      @QueryParam("noDefaultFieldA") Integer fieldA)
  {
    final int total = 3;
    List<HighLevelRecordWithDefault> elements = new ArrayList<>();
    for (int i = 0; i < total; i ++)
    {
      HighLevelRecordWithDefault record = new HighLevelRecordWithDefault().setNoDefaultFieldA(fieldA);
      elements.add(record);
    }
    LowLevelRecordWithDefault metadata = new LowLevelRecordWithDefault();
    return new CollectionResult<>(elements, total, metadata);
  }

  @BatchFinder(value = "searchRecords", batchParam = "criteria")
  public BatchFinderResult<RecordCriteria, HighLevelRecordWithDefault, Empty> searchRecords(
      @QueryParam("criteria") RecordCriteria[] criteria)
  {

    BatchFinderResult<RecordCriteria, HighLevelRecordWithDefault, Empty> result = new BatchFinderResult<>();
    for (int i = 0; i < criteria.length; i++)
    {
      List<HighLevelRecordWithDefault> currentCriteriaResult = Collections.singletonList(
          new HighLevelRecordWithDefault().setNoDefaultFieldA(criteria[i].getIntWithoutDefault()));
      CollectionResult<HighLevelRecordWithDefault, Empty> cr = new CollectionResult<>(
          currentCriteriaResult, currentCriteriaResult.size());
      result.putResult(criteria[i], cr);
    }
    return result;
  }

  @Action(name = "defaultFillAction")
  public ActionResult<HighLevelRecordWithDefault> takeAction(@ActionParam("actionParam") Long id)
  {
    return new ActionResult<>(new HighLevelRecordWithDefault().setNoDefaultFieldA(
        Math.toIntExact(id)),
        HttpStatus.S_200_OK);
  }
}
