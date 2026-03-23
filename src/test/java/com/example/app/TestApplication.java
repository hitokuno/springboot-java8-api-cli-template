package com.example.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Test-only entry point at the root package.
 * Required so that @MybatisTest (and other slice tests outside the api/ subtree)
 * can discover a @SpringBootConfiguration by walking up the package hierarchy.
 */
@SpringBootApplication
public class TestApplication {
}
