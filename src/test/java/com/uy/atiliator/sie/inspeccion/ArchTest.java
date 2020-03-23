package com.uy.atiliator.sie.inspeccion;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.uy.atiliator.sie.inspeccion");

        noClasses()
            .that()
                .resideInAnyPackage("com.uy.atiliator.sie.inspeccion.service..")
            .or()
                .resideInAnyPackage("com.uy.atiliator.sie.inspeccion.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.uy.atiliator.sie.inspeccion.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
