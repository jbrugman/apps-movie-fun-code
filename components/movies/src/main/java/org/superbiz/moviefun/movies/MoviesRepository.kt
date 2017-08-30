/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.superbiz.moviefun.movies

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager

@Repository
class MoviesRepository(val entityManager: EntityManager) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun addMovie(movie: Movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.title, movie.year)
        entityManager.persist(movie)
    }

    fun find(id: Long?): Movie {
        return entityManager.find(Movie::class.java, id)
    }

    @Transactional
    fun updateMovie(movie: Movie) {
        entityManager.merge(movie)
    }

    @Transactional
    fun deleteMovie(movie: Movie) {
        entityManager.remove(movie)
    }

    @Transactional
    fun deleteMovieId(id: Long) {
        val movie = entityManager.find<Movie>(Movie::class.java, id)
        deleteMovie(movie)
    }


    val movies: List<Movie>
        get() {
            val cq = entityManager.criteriaBuilder.createQuery(Movie::class.java)
            cq.select(cq.from(Movie::class.java))
            return entityManager.createQuery(cq).resultList
        }

    fun findAll(firstResult: Int, maxResults: Int): List<Movie> {
        val cq = entityManager.criteriaBuilder.createQuery<Movie>(Movie::class.java)
        cq.select(cq.from<Movie>(Movie::class.java))
        val q = entityManager.createQuery(cq)
        q.maxResults = maxResults
        q.firstResult = firstResult
        return q.resultList
    }

    fun countAll(): Int {
        val cq = entityManager.criteriaBuilder.createQuery<Long>(Long::class.java)
        val rt = cq.from<Movie>(Movie::class.java)
        cq.select(entityManager.criteriaBuilder.count(rt))
        val q = entityManager.createQuery(cq)
        return q.singleResult.toInt()
    }

    fun count(field: String, searchTerm: String): Int {
        val qb = entityManager.criteriaBuilder
        val cq = qb.createQuery<Long>(Long::class.java)
        val root = cq.from<Movie>(Movie::class.java)
        val type = entityManager.metamodel.entity<Movie>(Movie::class.java)

        val path = root.get(type.getDeclaredSingularAttribute<String>(field, String::class.java))
        val condition = qb.like(path, "%$searchTerm%")

        cq.select(qb.count(root))
        cq.where(condition)

        return entityManager.createQuery(cq).singleResult.toInt()
    }

    fun findRange(field: String, searchTerm: String, firstResult: Int, maxResults: Int): List<Movie> {
        val qb = entityManager.criteriaBuilder
        val cq = qb.createQuery<Movie>(Movie::class.java)
        val root = cq.from<Movie>(Movie::class.java)
        val type = entityManager.metamodel.entity<Movie>(Movie::class.java)

        val path = root.get(type.getDeclaredSingularAttribute<String>(field, String::class.java))
        val condition = qb.like(path, "%$searchTerm%")

        cq.where(condition)
        val q = entityManager.createQuery(cq)
        q.maxResults = maxResults
        q.firstResult = firstResult
        return q.resultList
    }

    fun clean() {
        entityManager.createQuery("delete from Movie").executeUpdate()
    }
}
