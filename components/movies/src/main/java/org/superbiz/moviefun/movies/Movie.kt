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

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import java.io.Serializable

@Entity
class Movie : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var director: String? = null
    var title: String? = null
    var year: Int = 0
    var genre: String? = null
    var rating: Int = 0

    constructor() {}

    constructor(title: String, director: String, genre: String, rating: Int, year: Int) {
        this.director = director
        this.title = title
        this.year = year
        this.genre = genre
        this.rating = rating
    }

    constructor(director: String, title: String, year: Int) {
        this.director = director
        this.title = title
        this.year = year
    }

    companion object {

        private const val serialVersionUID = 1L
    }
}
