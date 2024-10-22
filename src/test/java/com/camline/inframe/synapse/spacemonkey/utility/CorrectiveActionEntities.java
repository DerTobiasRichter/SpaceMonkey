package com.camline.inframe.synapse.spacemonkey.utility;

import com.camline.inframe.synapse.spacemonkey.model.space.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CorrectiveActionEntities {

    private final static String TRACKING_UNIT_NAME = "spacemonkey";
    private final static  Long SAMPLE_ID = 12345L;
    private final static String IMMEDIATE_ATTR = "immediate";

    private final static String AUTHOR = "Test User";
    private final static Long EVENT_DATE = new Date().getTime() - 3600;
    private final static Integer LDS_ID = 12345;
    private final static String USER_STATION = "Test Station Sirius AB1-a";

    public static CaSelectedSamplesData getSimpleCaSelectedSamplesData() {
        CaSelectedSamplesData simpleCaSelectedSamplesData = new CaSelectedSamplesData();
        simpleCaSelectedSamplesData.setCaContext(createCaContext());
        simpleCaSelectedSamplesData.getCaSamples().add(createShallowCaWithSamples());

        return simpleCaSelectedSamplesData;
    }

    private static CaContext createCaContext(){
        CaContext caContext = new CaContext();
        caContext.setAuthor(AUTHOR);
        caContext.setEventDate(EVENT_DATE);
        caContext.setLdsId(LDS_ID);
        caContext.setUserStation(USER_STATION);

        return caContext;
    }

    private static CaWithSamples createShallowCaWithSamples(){
        CaWithSamples sampleCAs = new CaWithSamples();
        sampleCAs.getSamples().add(createShallowSample());
        sampleCAs.setCorrectiveAction(createSimpleCorrectiveAction());

        return sampleCAs;
    }

    private static Sample createShallowSample() {
        final Sample sample = new Sample();
        sample.setSampleId(SAMPLE_ID);
        final List<SampleKey> extSampleKeys = new ArrayList<>();
        final SampleKey waferId = new SampleKey();
        waferId.setKeyName("WaferId");
        waferId.setKeyValue(TRACKING_UNIT_NAME);
        extSampleKeys.add(waferId);
        sample.setSampleExtractorKeys(extSampleKeys);
        return sample;
    }

    private static CorrectiveAction createSimpleCorrectiveAction() {
        final CorrectiveAction ca = new CorrectiveAction();
        ca.setCaName("ACT.UNIT:HOLD");
        final List<String> attributes = new ArrayList<>();
        attributes.add("ACT.UNIT:HOLD");
        attributes.add("ACT.UNIT:HOLD#reason=Hold Reason 1");
        attributes.add("ACT.UNIT:HOLD#code=Split Hold");
        attributes.add(IMMEDIATE_ATTR);
        ca.setCaAttributes(attributes);

        return ca;
    }
}
