package br.com.developen.ruralpatrol.exception;


import br.com.developen.ruralpatrol.util.Messaging;

public class CannotLoadPlacesException extends Exception implements Messaging {

    public CannotLoadPlacesException(){

        super("Não foi possível carregar os locais.");

    }

    public String[] getMessages(){

        return new String[]{getMessage()};

    }

}