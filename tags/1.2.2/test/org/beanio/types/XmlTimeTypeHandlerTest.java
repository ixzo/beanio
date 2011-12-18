/*
 * Copyright 2011 Kevin Seim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beanio.types;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.beanio.types.xml.XmlTimeTypeHandler;
import org.junit.Test;

/**
 * JUnit test cases for the <tt>XmlTimeTypeHandler</tt>.
 * 
 * @author Kevin Seim
 * @since 1.1
 * @see XmlTimeTypeHandler
 */
public class XmlTimeTypeHandlerTest {
    
    @Test
    public void testTime() throws TypeConversionException {
        XmlTimeTypeHandler handler = new XmlTimeTypeHandler();
        
        Date date = handler.parse("15:14:13");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(15, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(14, cal.get(Calendar.MINUTE));
        assertEquals(13, cal.get(Calendar.SECOND));
        assertEquals(0, cal.get(Calendar.MILLISECOND));
        
        assertEquals("15:14:13", handler.format(date));
    }
    
    @Test
    public void testTimeWithMilliseconds() throws TypeConversionException {
        XmlTimeTypeHandler handler = new XmlTimeTypeHandler();
        handler.setOutputMilliseconds(true);
        
        Date date = handler.parse("08:04:03.1236");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(8, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(4, cal.get(Calendar.MINUTE));
        assertEquals(3, cal.get(Calendar.SECOND));
        assertEquals(123, cal.get(Calendar.MILLISECOND));
        
        assertEquals("08:04:03.123", handler.format(date));
    }
    
    @Test
    public void testTimeWithTimezone() throws TypeConversionException {
        XmlTimeTypeHandler handler = new XmlTimeTypeHandler();
        handler.setTimeZoneId("GMT+1:00");
        
        Date date = handler.parse("23:04:03+01:00");
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        cal.setTime(date);
        assertEquals(23, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(4, cal.get(Calendar.MINUTE));
        assertEquals(3, cal.get(Calendar.SECOND));
        assertEquals(0, cal.get(Calendar.MILLISECOND));
        
        assertEquals("23:04:03+01:00", handler.format(date));
    }

    @Test(expected=TypeConversionException.class)
    public void testInvalidTime() throws TypeConversionException {
        XmlTimeTypeHandler handler = new XmlTimeTypeHandler();
        handler.parse("23:62:03+01:00");
    }
    
    @Test(expected=TypeConversionException.class)
    public void testInvalidTimeWithTimezone() throws TypeConversionException {
        XmlTimeTypeHandler handler = new XmlTimeTypeHandler();
        handler.setTimeZoneAllowed(false);
        handler.parse("23:04:03+01:00");
    }
}
