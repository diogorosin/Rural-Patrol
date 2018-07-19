package br.com.developen.ruralpatrol.util;

import br.com.developen.ruralpatrol.R;

public class IconUtils {

    public static int getListIconByType(int type){

        int result = 0;

        switch (type) {

            case 1: result = R.drawable.home_24;
                break;

            case 2: result = R.drawable.church_24;
                break;

            case 3: result = R.drawable.bar_24;
                break;

            case 4: result = R.drawable.school_24;
                break;

            case 5: result = R.drawable.soccer_24;
                break;

        }

        return result;

    }

    public static int getPinIconByType(int type){

        int result = 0;

        switch (type) {

            case 1: result = R.drawable.home_pin;
                break;

            case 2: result = R.drawable.church_pin;
                break;

            case 3: result = R.drawable.bar_pin;
                break;

            case 4: result = R.drawable.school_pin;
                break;

            case 5: result = R.drawable.soccer_pin;
                break;

        }

        return result;

    }

}
