package structure;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class StructurePluginTest {

    @Test
    public void realFileShouldOutputWarningsForEachImport() throws Exception {
/*
import org.apache.commons.lang3.StringUtils;                    import 1

import se.arbetsformedlingen.utils.log.Logger;                  import 2
import se.arbetsformedlingen.utils.log.LoggerFactory;           duplicate import

import se.arbetsformedlingen.elin.rapport.util.SomethingElse    same package import
import java.lang.reflect.Field;                                 import 3
import java.text.SimpleDateFormat;                              import 4
import java.util.*;                                             import 5

import static java.lang.String.format;                          import 6

 */

        StructurePlugin structurePlugin = new StructurePlugin("src/test/resources/ExcelCSVSkrivare.java");
        structurePlugin.process();
        List<String> collect = structurePlugin.getOutput().collect(Collectors.toList());
        assertThat(collect, contains(
                "se.arbetsformedlingen.elin.rapport.util.ExcelCSVSkrivare refers to package java.lang",
                "se.arbetsformedlingen.elin.rapport.util.ExcelCSVSkrivare refers to package java.lang.reflect",
                "se.arbetsformedlingen.elin.rapport.util.ExcelCSVSkrivare refers to package java.text",
                "se.arbetsformedlingen.elin.rapport.util.ExcelCSVSkrivare refers to package java.util",
                "se.arbetsformedlingen.elin.rapport.util.ExcelCSVSkrivare refers to package org.apache.commons.lang3",
                "se.arbetsformedlingen.elin.rapport.util.ExcelCSVSkrivare refers to package se.arbetsformedlingen.utils.log"
        ));
    }

}