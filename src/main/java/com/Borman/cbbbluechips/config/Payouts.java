package com.Borman.cbbbluechips.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="game-rules")
public class Payouts {

    private Map<String, String> payoutMap;

    public Map<String, String> getPayoutMap() {
        return payoutMap;
    }

    public void setPayoutMap(Map<String, String> payoutMap) {
        this.payoutMap = payoutMap;
    }

}