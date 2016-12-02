package com.kevinnoon.cylonapps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 29/06/2016 at 07:15.
 */
public class SiteTest {
    @Test
    public void compareTo() throws Exception {

    }

    @Test
    public void compare() throws Exception {
        Site site1 = new Site();
        site1.SiteNumber = 1;
        Site site2 = new Site();
        site2.SiteNumber = 2;
        Site site4 = new Site();
        site4.SiteNumber = 40;
        Site site5 = new Site();
        site5.SiteNumber = 5;

        List<Site> aSite = new ArrayList<>();
        aSite.add(site1);
        aSite.add(site2);
        aSite.add(site4);
        aSite.add(site5);

//        aSite.sort(new Comparator<Site>() {
//            @Override
//            public int compare(Site o1, Site o2) {
//                return  o1.SiteNumber - o2.SiteNumber;
//            }});

        aSite.sort((Site o1, Site o2) ->o1.SiteNumber - o2.SiteNumber);

    //    Arrays.sort(aSite);
        int nextNo = 0;
        for (int i = 0; i < aSite.size() - 1; i++) {
            nextNo = aSite.get(i).SiteNumber;
            if ((aSite.get(i + 1).SiteNumber - nextNo - 1) > 0){
                nextNo++;
                break;
            }
        }
    }

}