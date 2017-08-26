package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumsController {

    private AlbumsRepository albumsBean;

    public AlbumsController(AlbumsRepository albumsBean) {
        this.albumsBean = albumsBean;
    }

    @PostMapping
    public void addAlbum(@RequestBody Album album) {
        albumsBean.addAlbum(album);
    }

    @GetMapping
    public List<Album> index() {
        return albumsBean.getAlbums();
    }

    @GetMapping("/{albumId}")
    public Album details(@PathVariable long albumId) {
        return albumsBean.find(albumId);
    }
}