
package com.sunrisehms.service.custom;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.service.Service;
import java.util.List;


public interface CustomerService extends Service{
    Long save(CustomerDto customerDto) throws Exception;
    void update(CustomerDto customerDto) throws Exception;
    void delete(Long id) throws Exception;
    CustomerDto get(Long id) throws Exception;
    CustomerDto getByNIC(String nic) throws Exception;
    List<CustomerDto> getAll() throws Exception;
}
