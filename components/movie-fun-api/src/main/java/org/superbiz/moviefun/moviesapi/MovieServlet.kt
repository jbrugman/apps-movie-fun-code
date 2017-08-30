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
package org.superbiz.moviefun.moviesapi

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

@Component
class MovieServlet(private val moviesClient: MoviesClient) : HttpServlet() {

    @Value("\${movies.pagesize}")
    var pageSize: Int = 0

    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        process(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        process(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    private fun process(request: HttpServletRequest, response: HttpServletResponse) {
        val action = request.getParameter("action")

        if ("Add" == action) {

            val title = request.getParameter("title")
            val director = request.getParameter("director")
            val genre = request.getParameter("genre")
            val rating = Integer.parseInt(request.getParameter("rating"))
            val year = Integer.parseInt(request.getParameter("year"))

            val movie = MovieInfo(null, title, director, genre, rating, year)

            moviesClient.addMovie(movie)
            response.sendRedirect("moviefun")
            return

        } else if ("Remove" == action) {

            val ids = request.getParameterValues("id")
            for (id in ids) {
                moviesClient.deleteMovieId(id.toLong())
            }

            response.sendRedirect("moviefun")
            return

        } else {
            var key = request.getParameter("key")
            var field = request.getParameter("field")

            var count = 0

            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
                count = moviesClient.countAll()
                key = ""
                field = ""
            } else {
                count = moviesClient.count(field, key)
            }

            var page = 1

            try {
                page = Integer.parseInt(request.getParameter("page"))
            } catch (e: Exception) {
            }

            var pageCount = count / pageSize
            if (pageCount == 0 || count % pageSize != 0) {
                pageCount++
            }

            if (page < 1) {
                page = 1
            }

            if (page > pageCount) {
                page = pageCount
            }

            val start = (page - 1) * pageSize
            val range: List<MovieInfo>

            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
                range = moviesClient.findAll(start, pageSize)
            } else {
                range = moviesClient.findRange(field, key, start, pageSize)
            }

            val end = start + range.size

            request.setAttribute("count", count)
            request.setAttribute("start", start + 1)
            request.setAttribute("end", end)
            request.setAttribute("page", page)
            request.setAttribute("pageCount", pageCount)
            request.setAttribute("movies", range)
            request.setAttribute("key", key)
            request.setAttribute("field", field)
        }

        request.getRequestDispatcher("WEB-INF/moviefun.jsp").forward(request, response)
    }

    companion object {

        private val serialVersionUID = -5832176047021911038L
    }

}