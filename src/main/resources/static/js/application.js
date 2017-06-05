/**
 * Created by junyuan on 06/05/2017.
 */
$(function () {
    // function showMovie(title) {
    //     $.get("/movies/search/findByTitle?title=" + encodeURIComponent(title),
    //         function (data) {
    //             if (!data ) return; //  || !data["_embedded"].movies) return;
    //             var movie = data; // ["_embedded"].movies[0];
    //             $("#title").text(movie.title);
    //             $("#poster").attr("src","http://neo4j-contrib.github.io/developer-resources/language-guides/assets/posters/"+encodeURIComponent(movie.title)+".jpg");
    //             var $list = $("#crew").empty();
    //             movie.roles.forEach(function (cast) {
    //                 $.get(cast._links.person.href, function(personData) {
    //                     var person = personData.name;
    //                     var job = cast.job || "acted";
    //                     $list.append($("<li>" + person + " " +job + (job == "acted"?" as " + cast.roles.join(", ") : "") + "</li>"));
    //                 });
    //             });
    //         }, "json");
    //     return false;
    // }
    //
    // function search() {
    //     var query=$("#search").find("input[name=search]").val();
    //     $.get("/movies/search/findByTitleLike?title=*" + encodeURIComponent(query) + "*",
    //         function (data) {
    //             var t = $("table#results tbody").empty();
    //             if (!data) return;
    //             data = data["_embedded"].movies;
    //             data.forEach(function (movie) {
    //                 $("<tr><td class='movie'>" + movie.title + "</td><td>" + movie.released + "</td><td>" + movie.tagline + "</td></tr>").appendTo(t)
    //                     .click(function() { showMovie($(this).find("td.movie").text());})
    //             });
    //             showMovie(data[0].title);
    //         }, "json");
    //     return false;
    // }
    //
    // $("#search").submit(search);
    // search();

    function showEventAndPeople(question) {
        $.get("/movies/search/findByTitle?title=" + encodeURIComponent(title),
            function (data) {
                if (!data ) return; //  || !data["_embedded"].movies) return;
                var movie = data; // ["_embedded"].movies[0];
                $("#title").text(movie.title);
                $("#poster").attr("src","http://neo4j-contrib.github.io/developer-resources/language-guides/assets/posters/"+encodeURIComponent(movie.title)+".jpg");
                var $list = $("#crew").empty();
                movie.roles.forEach(function (cast) {
                    $.get(cast._links.person.href, function(personData) {
                        var person = personData.name;
                        var job = cast.job || "acted";
                        $list.append($("<li>" + person + " " +job + (job == "acted"?" as " + cast.roles.join(", ") : "") + "</li>"));
                    });
                });
            }, "json");
        return false;
    }

    // function search() {
    //     var query=$("#search").find("input[name=search]").val();
    //     query = "Matrix"
    //     console.log("/movies/search/findByTitleLike?title=*" + encodeURIComponent(query) + "*")
    //
    //     $.get("/movies/search/findByTitleLike?title=*" + encodeURIComponent(query) + "*",
    //         function (data) {
    //             console.log(data)
    //             var t = $("table#results tbody").empty();
    //             if (!data) return;
    //             data = data["_embedded"].movies;
    //             data.forEach(function (movie) {
    //                 $("<tr><td class='movie'>" + movie.title + "</td><td>" + movie.released + "</td><td>" + movie.tagline + "</td></tr>").appendTo(t)
    //                     .click(function() { showMovie($(this).find("td.movie").text());})
    //             });
    //             showMovie(data[0].title);
    //         }, "json");
    //     return false;
    // }

    // $("#search").submit(search);
    // search();
    
    function search(url) {
        console.log("in search process");
        d3.json(url, function (error, data) {

        })
    }
    
    $("#search-btn").click(function() {
        var to_search = $("#search-text").val();
        while (to_search.charAt(0) == ' ') {
            to_search = to_search.substr(1);
        }
        while (to_search.indexOf(' ') > 0) {
            to_search = to_search.replace(' ', '+');
        }
        var url = "/search/" + to_search;

        console.log(url)
        search(url)
        $("#search-text").val("");
    })

    function save(url) {
        console.log("in save process")

        d3.json(url, function (error, data) {
            if (error) return;
        })
    }
    
    $("#addbtn").click(function () {
        var to_save = $("#add-text").val();
        while (to_save.charAt(0) == ' ') {
            to_save = to_save.substr(1);
        }
        while (to_save.indexOf(' ') > 0) {
            to_save = to_save.replace(' ', '+');
        }
        var url = "/save/" + to_save;

        console.log(url)
        save(url)
        $("#add-text").val("");
    })

    // $("#add").submit(save())
})