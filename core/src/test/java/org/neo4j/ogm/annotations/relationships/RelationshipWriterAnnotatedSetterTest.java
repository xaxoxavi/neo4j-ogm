/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 *  conditions of the subcomponent's license, as noted in the LICENSE file.
 */

package org.neo4j.ogm.annotations.relationships;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.entity.io.DefaultEntityAccessStrategy;
import org.neo4j.ogm.entity.io.MethodWriter;
import org.neo4j.ogm.entity.io.RelationalWriter;
import org.neo4j.ogm.metadata.ClassInfo;
import org.neo4j.ogm.metadata.DomainInfo;

/**
 * @author Vince Bickers
 */
public class RelationshipWriterAnnotatedSetterTest {

    private DefaultEntityAccessStrategy entityAccessStrategy = new DefaultEntityAccessStrategy();
    private DomainInfo domainInfo = new DomainInfo(this.getClass().getPackage().getName());

    @Test
    public void shouldFindWriterForCollection() {

        ClassInfo classInfo = this.domainInfo.getClass(S.class.getName());

        RelationalWriter objectAccess = this.entityAccessStrategy.getRelationalWriter(classInfo, "LIST", Relationship.OUTGOING, new T());
        assertNotNull("The resultant object accessor shouldn't be null", objectAccess);
        assertTrue("The access mechanism should be via the method", objectAccess instanceof MethodWriter);
        assertEquals("LIST", objectAccess.relationshipName());
        assertEquals(List.class, objectAccess.type());

    }

    @Test
    public void shouldFindWriterForScalar() {

        ClassInfo classInfo = this.domainInfo.getClass(S.class.getName());

        RelationalWriter objectAccess = this.entityAccessStrategy.getRelationalWriter(classInfo, "SCALAR", Relationship.OUTGOING, new T());
        assertNotNull("The resultant object accessor shouldn't be null", objectAccess);
        assertTrue("The access mechanism should be via the method", objectAccess instanceof MethodWriter);
        assertEquals("SCALAR", objectAccess.relationshipName());
        assertEquals(T.class, objectAccess.type());

    }


    @Test
    public void shouldFindWriterForArray() {

        ClassInfo classInfo = this.domainInfo.getClass(S.class.getName());

        RelationalWriter objectAccess = this.entityAccessStrategy.getRelationalWriter(classInfo, "ARRAY", Relationship.OUTGOING, new T());
        assertNotNull("The resultant object accessor shouldn't be null", objectAccess);
        assertTrue("The access mechanism should be via the method", objectAccess instanceof MethodWriter);
        assertEquals("ARRAY", objectAccess.relationshipName());
        assertEquals(T[].class, objectAccess.type());

    }

    static class S {

        Long id;

        List<T> list;
        T[] array;
        T scalar;

        @Relationship(type = "LIST", direction = Relationship.OUTGOING)
        public void setList(List<T> list) {
            this.list = list;
        }

        @Relationship(type = "ARRAY", direction = Relationship.OUTGOING)
        public void setArray(T[] array) {
            this.array = array;
        }

        @Relationship(type = "SCALAR", direction = Relationship.OUTGOING)
        public void setScalar(T scalar) {
            this.scalar = scalar;
        }

    }

    static class T {

        Long id;

    }

}
