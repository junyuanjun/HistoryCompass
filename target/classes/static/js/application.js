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

    function search() {
        var query=$("#search").find("input[name=search]").val();
        query = "Matrix"
        console.log("/movies/search/findByTitleLike?title=*" + encodeURIComponent(query) + "*")

        $.get("/movies/search/findByTitleLike?title=*" + encodeURIComponent(query) + "*",
            function (data) {
                console.log(data)
                var t = $("table#results tbody").empty();
                if (!data) return;
                data = data["_embedded"].movies;
                data.forEach(function (movie) {
                    $("<tr><td class='movie'>" + movie.title + "</td><td>" + movie.released + "</td><td>" + movie.tagline + "</td></tr>").appendTo(t)
                        .click(function() { showMovie($(this).find("td.movie").text());})
                });
                showMovie(data[0].title);
            }, "json");
        return false;
    }

    $("#search").submit(search);
    // search();

    function save() {
        var to_save = $("#add").find("input[name=add]").val();
        to_save.replace(" ", "+");
        console.log(to_save)
        d3.json("/save/" + to_save, function (error, data) {
            if (error) return;
        })
    }

    $("#add").submit(save())
})