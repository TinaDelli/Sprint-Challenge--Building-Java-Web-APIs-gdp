package com.lambdaschool.gdp.controller;


import com.lambdaschool.gdp.GdpApplication;
import com.lambdaschool.gdp.exception.ResourceNotFoundException;
import com.lambdaschool.gdp.model.GDP;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@RestController
@RequestMapping("/gdp")
public class GDPController
{
    private static final Logger logger = LoggerFactory.getLogger(GDPController.class);


    // localhost:2017//gdp/names/
    @GetMapping(value = "/names",
                produces = {"application/json"})
    public ResponseEntity<?> getAllGDPs(HttpServletRequest request)
    {
        logger.info(request.getRequestURI() + "/names accessed");

        GdpApplication.ourGdpList.gdpList.sort((g1, g2) -> (int) (g1.getCountry().compareToIgnoreCase(g2.getCountry())));
        return new ResponseEntity<>(GdpApplication.ourGdpList.gdpList, HttpStatus.OK);
    }

    // localhost:2017/gdp/country/{id}
    @GetMapping(value = "country/{id}",
                produces = {"application/json"})
    public ResponseEntity<?> getCountryDetail(HttpServletRequest request,
                                              @PathVariable
                                                      long id)
    {
        logger.info(request.getRequestURI() + "/country " + id + " accessed");

        GDP rtnGDP;
        if (GdpApplication.ourGdpList.findGDP(g -> (g.getId() == id)) == null)
        {
            throw new ResourceNotFoundException("Country with id " + id + " not found");
        } else
        {
            rtnGDP = GdpApplication.ourGdpList.findGDP(g -> (g.getId() == id));
        }
        return new ResponseEntity<>(rtnGDP, HttpStatus.OK);
    }

    // localhost:2017/gdp/names/list/{country}
    @GetMapping(value = "/names/list/{country}")
    public ResponseEntity<?> getCountries(HttpServletRequest request,
                                          @PathVariable
                                                  String country)
    {
        logger.info(request.getRequestURI() + "/gdp/names/list " + country + " accessed");

        ArrayList<GDP> rtnGDP = GdpApplication.ourGdpList.findGDPs(g -> g.getCountry().toUpperCase().equals(country.toUpperCase()));
        if (rtnGDP.size() == 0)
        {
            throw new ResourceNotFoundException(country + " is not a country we list");
        }
        return new ResponseEntity<>(rtnGDP, HttpStatus.OK);
    }

    // localhost:2017/gdp/names/s
    @GetMapping(value = "/names/{letter}",
                produces = {"application/json"})
    public ResponseEntity<?> getCountriesByLetter(HttpServletRequest request,
            @PathVariable
                    char letter)
    {
        logger.info(request.getRequestURI() + "/gdp/names/ " + letter + " accessed");
        ArrayList<GDP> rtnGDP = GdpApplication.ourGdpList.
                findGDPs(g -> g.getCountry().toUpperCase().charAt(0) == Character.toUpperCase(letter));

        if (rtnGDP.size() == 0)
        {
            throw new ResourceNotFoundException("No countries start with " + letter);
        }
        return new ResponseEntity<>(rtnGDP, HttpStatus.OK);
    }

    // localhost:2017/gdp/country/stats/median
    @GetMapping(value = "/country/stats/median", produces = {"application/json"})
    public ResponseEntity<?>findMedianGDP(HttpServletRequest request)
    {
        logger.info(request.getRequestURI() + "/country/stats/median accessed");

        GDP rtnGDP;
        GdpApplication.ourGdpList.gdpList.sort((g1,g2) -> (int)(Long.parseLong(g1.getGdp())- (Long.parseLong(g2.getGdp()))));

        rtnGDP = GdpApplication.ourGdpList.gdpList.get((GdpApplication.ourGdpList.gdpList.size()/2)+1);
        return new ResponseEntity<>(rtnGDP, HttpStatus.OK);
    }

    //localhost:2017/gdp/economy
    @GetMapping(value = "/economy", produces = {"application/json"})
    public ResponseEntity<?>findEconomy(HttpServletRequest request)
    {
        logger.info(request.getRequestURI() + "/economy accessed");

        GdpApplication.ourGdpList.gdpList.sort((e1,e2)->(int)(Long.parseLong(e2.getGdp()) - (Long.parseLong(e1.getGdp()))));
        return new ResponseEntity<>(GdpApplication.ourGdpList.gdpList, HttpStatus.OK);
    }

//    localhost:2017/gdp/economy/table
        @GetMapping(value = "/economy/table")
        public ModelAndView displayGDPTable(HttpServletRequest request)
        {
            logger.trace(request.getRequestURI() + " accessed");

            ModelAndView mav = new ModelAndView();
            mav.setViewName("gdp");
            mav.addObject("gdpList", GdpApplication.ourGdpList.gdpList);

            return mav;
        }
}


