package com.lambdaschool.gdp.model;

import java.util.concurrent.atomic.AtomicLong;

public class GDP
{
    private static final AtomicLong counter = new AtomicLong();
    private long id;
    private String country;
    private String gdp;

    public GDP()
    {
    }

    public GDP(String country, String gdp)
    {
        this.id = counter.incrementAndGet();
        this.country = country;
        this.gdp = gdp;
    }

    public GDP(GDP toClone)
    {
        this.id=toClone.getId();
        this.country = toClone.getCountry();
        this.gdp = toClone.gdp;
    }

    public long getId()
    {
        return id;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getGdp()
    {
        return gdp;
    }

    public void setGdp(String gdp)
    {
        this.gdp = gdp;
    }
}


