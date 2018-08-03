package com.ciandt.techgallery.technology

import com.ciandt.techgallery.utils.TechGalleryUtil
import com.google.appengine.api.utils.SystemProperty
import spock.lang.Specification

class TechnologyUtilSpec extends Specification {

    def "Get Application name"() {
        given: "a specific set applicationVersion on SystemProperty "
        SystemProperty.applicationVersion.set(config)

        expect: "the a version "
        TechGalleryUtil.getApplicationVersion() == version

        where: "some of the possible scenarios are"
        config      || version
        "test"      || "test"
        "test-1.0.0"|| "test"
        "test\$"    || "test\$"
    }

}
