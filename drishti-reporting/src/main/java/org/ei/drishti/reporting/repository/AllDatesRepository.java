package org.ei.drishti.reporting.repository;

import org.ei.drishti.reporting.domain.Dates;
import org.ei.drishti.reporting.repository.cache.DatesCacheableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.ei.drishti.reporting.domain.Dates.FIND_DATES_BY_DATE;

@Repository
@Transactional
public class AllDatesRepository implements DatesCacheableRepository {
    private DataAccessTemplate template;

    protected AllDatesRepository() {
    }

    @Autowired
    public AllDatesRepository(DataAccessTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Dates objectToBeSaved) {
        template.save(objectToBeSaved);
    }

    @Override
    public Dates fetch(Dates objectWhichShouldBeFilledWithMoreInformation) {
        return (Dates) template.getUniqueResult(FIND_DATES_BY_DATE, new String[] {"date"}, new Object[] {objectWhichShouldBeFilledWithMoreInformation.date()});
    }
}