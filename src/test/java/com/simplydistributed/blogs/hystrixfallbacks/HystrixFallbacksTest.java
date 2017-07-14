package com.simplydistributed.blogs.hystrixfallbacks;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HystrixFallbacksTest {
    @Test
    public void HystrixFallbackBasic_usesTheFallback() throws Exception {
        assertThat(new HystrixFallbacks.HystrixFallbackBasic(false).execute()).isEqualTo("Success");
        assertThat(new HystrixFallbacks.HystrixFallbackBasic(true).execute()).isEqualTo("Fallback");
    }

    @Test
    public void HystrixFallbackChain_usesTheChain() throws Exception {
        assertThat(new HystrixFallbacks.HystrixFallbackChain(false).execute()).isEqualTo("Success");
        assertThat(new HystrixFallbacks.HystrixFallbackChain(true).execute()).isEqualTo("Fallback HystrixCommand");
    }
}