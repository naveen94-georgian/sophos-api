package com.cgi.sophos;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SophosIT {
    @Test
    public void testIT(){
        System.out.println("Integration Test");
        Assertions.assertThat(true).isTrue();
    }
}
