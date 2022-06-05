package com.griddynamics.gridu.qa.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

public class Parser {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static <T> T parseJson(String pathname, Class<T> objectClass) throws Exception {

        return MAPPER.readValue(new File(pathname), objectClass);

//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
//        mapper.setDateFormat(dateFormat);

//        GregorianCalendar cal = new GregorianCalendar();
//        cal.setTime(new Date());
//        XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);

//        //XMLGregorianCalendar birthday
//        XMLGregorianCalendar birthday = DatatypeFactory.newInstance()
//                .newXMLGregorianCalendar(xml.getElementsByTagName("birthday").item(0).getTextContent());
    }
}
