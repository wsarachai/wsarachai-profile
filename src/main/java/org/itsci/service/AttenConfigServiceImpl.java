package org.itsci.service;

import org.itsci.dao.AttenConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttenConfigServiceImpl implements AttenConfigService {

    @Autowired
    private AttenConfigDao attenConfigDao;

    @Override
    @Transactional
    public int getYearMin() {
        String value = attenConfigDao.getOptionValueByName("yearMinOption");
        return Integer.parseInt(value);
    }

    @Override
    @Transactional
    public int getYearMax() {
        String value = attenConfigDao.getOptionValueByName("yearMaxOption");
        return Integer.parseInt(value);
    }
}
