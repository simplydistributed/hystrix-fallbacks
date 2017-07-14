package com.simplydistributed.blogs.hystrixfallbacks;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixFallbacks {
    public static class HystrixFallbackBasic extends HystrixCommand<String> {
        private final boolean fail;

        protected HystrixFallbackBasic(boolean fail) {
            super(HystrixCommandGroupKey.Factory.asKey("HystrixFallbackBasic"));
            this.fail = fail;
        }

        @Override
        protected String run() throws Exception {
            if (fail) {
                throw new Exception("My exception");
            }
            return "Success";
        }

        @Override
        protected String getFallback() {
            // Return the fallback in case of error.
            return "Fallback";
        }
    }

    public static class HystrixFallbackChain extends HystrixCommand<String> {
        private final boolean fail;
        protected HystrixFallbackChain(boolean fail) {
            super(HystrixCommandGroupKey.Factory.asKey("HystrixFallbackChain"));
            this.fail = fail;
        }

        @Override
        protected String run() throws Exception {
            if (fail) {
                throw new Exception("My exception");
            }
            return "Success";
        }

        @Override
        protected String getFallback() {
            System.out.println("Running chained fallback");
            return new HystrixFallbackChainFallback().execute();
        }
    }

    public static class HystrixFallbackChainFallback extends HystrixCommand<String> {
        protected HystrixFallbackChainFallback() {
            super(HystrixCommandGroupKey.Factory.asKey("HystrixFallbackChain"));
        }

        @Override
        protected String run() throws Exception {
            // This could run any network command (like accessing Memcache) with it's own fallback.
            return "Fallback HystrixCommand";
        }
    }
}
