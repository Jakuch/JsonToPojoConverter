package com.example.JsonToPojoConverter;

import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public DefaultGenerationConfig defaultGenerationConfig() {
        return new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };
    }

    @Bean
    public Jackson2Annotator jackson2Annotator(DefaultGenerationConfig config) {
        return new Jackson2Annotator(config);
    }

    @Bean
    public SchemaStore schemaStore() {
        return new SchemaStore();
    }

    @Bean
    public SchemaGenerator schemaGenerator() {
        return new SchemaGenerator();
    }

    @Bean
    public SchemaMapper schemaMapper(DefaultGenerationConfig config, Jackson2Annotator jackson2Annotator, SchemaStore schemaStore, SchemaGenerator schemaGenerator) {
        return new SchemaMapper(new RuleFactory(config, jackson2Annotator, schemaStore), schemaGenerator);
    }
}
