package com.example.timesheet.service;

import com.example.common.util.SearchUtil;
import com.example.timesheet.dto.timesheet.AddTypeTimeSheet;
import com.example.timesheet.dto.timesheet.GetAllTimeSheet;
import com.example.timesheet.dto.timesheet.TimeSheetRequest;
import com.example.timesheet.dto.timesheet.UpdateTimeSheet;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.repo.TimeSheetRepo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Pham Duc Manh
 *
 */

@Service
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService{

    @Autowired
    TimeSheetRepo timesheetRepo;


//    @Override
//    public List<TimeSheet> getAll() {
//        List<TimeSheet> listTimeSheet = new ArrayList<>();
//        listTimeSheet = timesheetRepo.findAll();
//       return  listTimeSheet;
//    }

    @Override
    public List<GetAllTimeSheet> getAll() {
        try {
            List<TimeSheet> ts = timesheetRepo.findAll();
            List<GetAllTimeSheet> getall = new ArrayList<>();
            ts.forEach(timesheet -> {
                GetAllTimeSheet allTS = new GetAllTimeSheet();
                allTS.setId(timesheet.getId());
                allTS.setPercent(timesheet.getPercent());
                allTS.setType(timesheet.getTypeTimeSheet());
                getall.add(allTS);
            });
            return getall;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public TimeSheet create(@Valid AddTypeTimeSheet addTypeTimeSheet) {
        try {
            Optional<TimeSheet> optionalTimeSheet = timesheetRepo.findBytypeTimeSheet(addTypeTimeSheet.getTypeTimeSheet());
            if(optionalTimeSheet.isPresent()){
                throw new DuplicateKeyException("common.error.dupplicate");
            } else {
                TimeSheet timeSheet = new TimeSheet();
                timeSheet.setTypeTimeSheet(addTypeTimeSheet.getTypeTimeSheet());
                timeSheet.setPercent(addTypeTimeSheet.getPercent());
                return timesheetRepo.save(timeSheet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public TimeSheet update(@Valid UpdateTimeSheet updateTimeSheet) {
        try {

            Optional<TimeSheet> optionalTimeSheet = timesheetRepo.findById(updateTimeSheet.getId());
            if(!optionalTimeSheet.isPresent()){
                throw new NotFoundException("common.error.not-found");
            } else {
                try {
                    TimeSheet ts = optionalTimeSheet.get();
                    PropertyUtils.copyProperties(ts, updateTimeSheet);
                    return timesheetRepo.save(ts);
                }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public TimeSheet delete(String id) {
        try {
            Optional<TimeSheet> optionalTimeSheet = timesheetRepo.findById(id);
            if (optionalTimeSheet.isPresent()) {
                TimeSheet entityCusomer = new TimeSheet();
                BeanUtils.copyProperties(entityCusomer,optionalTimeSheet );
                entityCusomer.setDeleted(false);
                timesheetRepo.save(entityCusomer);
                return entityCusomer;
            } else {
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Page<TimeSheet> advanceSearch(String filter, TimeSheetRequest searchRequest, Pageable pageable) {
        if (searchRequest != null) {
            List<Specification<TimeSheet>> specList = getAdvanceSearchSpecList(searchRequest);
            if (filter != null && !filter.isEmpty()) {
                specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
            }
            if (specList.size() > 0) {
                Specification<TimeSheet> spec = specList.get(0);
                for (int i = 1; i < specList.size(); i++) {
                    spec = spec.and(specList.get(i));
                }
                return timesheetRepo.findAll(spec, pageable);
            }
        }
        return timesheetRepo.findAll(pageable);
    }
    private List<Specification<TimeSheet>> getAdvanceSearchSpecList(@Valid TimeSheetRequest t) {
        List<Specification<TimeSheet>> specList = new ArrayList<>();
        if (t.getType() != null && !t.getType().isEmpty()) {
            specList.add(SearchUtil.like("type", "%" + t.getType() + "%"));
        }
        if (t.getIsActive() != null) {
            specList.add(SearchUtil.eq("isActive", t.getIsActive()));
        }
        if (t.getCreateDate() != null) {
            if (t.getCreateDate().getFromValue() != null) {
                specList.add(SearchUtil.ge("createdDate", t.getCreateDate().getFromValue()));
            }
            if (t.getCreateDate().getFromValue() != null) {
                specList.add(SearchUtil.lt("createdDate", t.getCreateDate().getToValue()));
            }
        }
        return specList;
    }
}
