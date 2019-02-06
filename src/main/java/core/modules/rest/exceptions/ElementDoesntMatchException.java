package core.modules.rest.exceptions;


public class ElementDoesntMatchException extends Exception
{
    public ElementDoesntMatchException(String elementName, String[] values) {
        super("Element " + elementName + "seeInTable wrong value. It should be:" + values.toString());
    }
}
