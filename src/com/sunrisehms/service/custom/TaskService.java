
package com.sunrisehms.service.custom;

import com.sunrisehms.dto.TaskDto;
import com.sunrisehms.service.Service;
import java.util.List;

public interface TaskService extends Service{
    TaskDto get(Integer id) throws Exception;
    List<TaskDto> getAll() throws Exception;
}
