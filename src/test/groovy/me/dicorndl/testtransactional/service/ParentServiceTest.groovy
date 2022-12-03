package me.dicorndl.testtransactional.service

import me.dicorndl.testtransactional.domain.Child
import me.dicorndl.testtransactional.domain.Parent
import me.dicorndl.testtransactional.domain.ParentRepository
import org.hibernate.LazyInitializationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class ParentServiceTest extends Specification {

    @Autowired
    ParentRepository parentRepository

    ParentService sut

    void setup() {
        sut = new ParentService(parentRepository)
    }

    void cleanup() {
        parentRepository.deleteAll()
    }

    def "No transactional"() {
        given:
        def parent = new Parent()
        def child = new Child(parent: parent)
        parent.children = [child] as Set
        parentRepository.saveAndFlush(parent)

        when:
        sut.countChildren(parent.id)

        then:
        thrown(LazyInitializationException) // ㅠㅠ
    }

    def "No transactional with fetch"() {
        given:
        def parent = new Parent()
        def child = new Child(parent: parent)
        parent.children = [child] as Set
        parentRepository.saveAndFlush(parent)

        when:
        def cnt = sut.countChildrenWithFetch(parent.id)

        then:
        cnt == 1
    }

    @Transactional
    def "With transactional"() {
        given:
        def parent = new Parent()
        def child = new Child(parent: parent)
        parent.children = [child] as Set
        parentRepository.saveAndFlush(parent)

        when:
        def cnt = sut.countChildren(parent.id)

        then:
        cnt == 1
    }
}
