package com.example.safesenderapp;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<Countries> getCountriesList(){
        List<Countries> countries=new ArrayList<>();

        Countries us=new Countries();
        us.setName("USD");
        us.setImage(R.drawable.icus);
        countries.add(us);

        Countries uk=new Countries();
        uk.setName("GBP");
        uk.setImage(R.drawable.icuk);
        countries.add(uk);

        Countries uae=new Countries();
        uae.setName("AED");
        uae.setImage(R.drawable.icuae);
        countries.add(uae);

        Countries turkey=new Countries();
        turkey.setName("TRY");
        turkey.setImage(R.drawable.icturkey);
        countries.add(turkey);

        Countries sk=new Countries();
        sk.setName("KRW");
        sk.setImage(R.drawable.icsouthkorea);
        countries.add(sk);

        Countries russia=new Countries();
        russia.setName("RUB");
        russia.setImage(R.drawable.icrussia);
        countries.add(russia);

        Countries qatar=new Countries();
        qatar.setName("QAR");
        qatar.setImage(R.drawable.icqatar);
        countries.add(qatar);

        Countries Pakistan=new Countries();
        Pakistan.setName("PKR");
        Pakistan.setImage(R.drawable.icpakistan);
        countries.add(Pakistan);

        Countries nz=new Countries();
        nz.setName("NZD");
        nz.setImage(R.drawable.icnewzealand);
        countries.add(nz);

        Countries kuwait=new Countries();
        kuwait.setName("KWD");
        kuwait.setImage(R.drawable.ickuwait);
        countries.add(kuwait);

        Countries euro=new Countries();
        euro.setName("EUR");
        euro.setImage(R.drawable.iceuro);
        countries.add(euro);

        Countries china=new Countries();
        china.setName("CNY");
        china.setImage(R.drawable.icchina);
        countries.add(china);

        Countries canada=new Countries();
        canada.setName("CAD");
        canada.setImage(R.drawable.iccanada);
        countries.add(canada);

        Countries brazil=new Countries();
        brazil.setName("BRL");
        brazil.setImage(R.drawable.icbrazil);
        countries.add(brazil);

        Countries aus=new Countries();
        aus.setName("AUD");
        aus.setImage(R.drawable.icaustralia);
        countries.add(aus);

        Countries arg=new Countries();
        arg.setName("ARS");
        arg.setImage(R.drawable.icargentina);
        countries.add(arg);
        return countries;
    }
}
