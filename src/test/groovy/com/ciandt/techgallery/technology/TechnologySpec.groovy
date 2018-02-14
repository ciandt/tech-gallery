package com.ciandt.techgallery.service

import com.ciandt.techgallery.persistence.model.Technology
import spock.lang.Specification


class TechnologySpec extends Specification {

    def "Parser technology id"() {
        when: "a technology name has only special character "
        Technology tech = new Technology();
        tech.name = "#._/";

        then: "its converted id must be empty"
        tech.convertNameToId(tech.getName()) == "___"
    }

    def "Parser technologies name"() {
        given: "a technology name with special character "
        Technology tech = new Technology();
        tech.with {
            name = name
        }

        expect: "the propert name with no special character"
        tech.convertNameToId(name) == parsedName

        where: "some of the possible scenarios are"
        name            || parsedName
        "angular js"    || "angular_js"
        "SQL Server"    || "sql_server"
        "Subver (SVN)"  || "subver_(svn)"
        "Node.JS"       || "nodejs"
        ".net"          || "net"
    }


}
