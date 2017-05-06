/**
 * Created by junyuan on 06/05/2017.
 */
$(function () {
    function showMovie(title) {
        $.get("/movies/search/findByTitle?title=" + encodeURIComponent(title), // todo fix paramter in SDN
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
        $.get("/movies/search/findByTitleLike?title=*" + encodeURIComponent(query) + "*",
            function (data) {
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
    search();
})