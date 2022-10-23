package org.limmen.hero.domain;

@FunctionalInterface
public interface PromptProvider {

  String ask(String prompt);
}
