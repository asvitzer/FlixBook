package com.alvinsvitzer.flixbook.moviedetail;

import com.alvinsvitzer.flixbook.data.AppRepository;
import com.alvinsvitzer.flixbook.data.model.Movie;
import com.alvinsvitzer.flixbook.data.model.Trailer;
import com.alvinsvitzer.flixbook.data.remote.MovieRemoteDataStore;
import com.android.volley.toolbox.ImageLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by Alvin on 2/21/17.
 */

@RunWith(PowerMockRunner.class)
public class MovieDetailsPresenterTest implements
         MovieRemoteDataStore.GetTrailersCallback
        , MovieRemoteDataStore.GetMovieCallback{

    private Movie mMovie;

    @Mock
    private AppRepository mAppRepository;

    @Mock
    private ImageLoader mImageLoader;

    @Mock
    private MovieDetailsContract.View mView;

    private MovieDetailPresenter mPresenter;

    @Before
    public void setupObject(){

        MockitoAnnotations.initMocks(this);

        mPresenter = new MovieDetailPresenter(mAppRepository, mImageLoader, mView);

        mMovie = new Movie();
        mMovie.setMovieTitle("The Best Movie");
    }

    @Test
    public void verifyViewIsDetached(){

        mPresenter.detachView();
        assertNull(mPresenter.mView);

    }

    @Test
    public void verifyViewIsAttached(){

        mPresenter.attachView(mView);

        assertEquals(mPresenter.mView, mView);

    }

    @Test
    public void verifyTrailersLoaded(){

        MovieDetailPresenter movieDetailPresenter = spy(mPresenter);

        movieDetailPresenter.onTrailersLoaded(anyList());

        verify(movieDetailPresenter).getOfficialYouTubeTrailerUrl();
        verify(movieDetailPresenter).mMovie.setTrailerList(anyList());

    }

    @Test
    public void trailerDataNotAvailable(){

        mPresenter.onTrailerDataNotAvailable();
        verify(mView).notifyUserNoTrailer();

    }

    @Test
    public void verifyMovieLoaded(){

        mPresenter.onMovieLoaded(mMovie);

        assertEquals(mPresenter.mMovie, mMovie);

    }

    @Test
    public void movieDataNotAvailable(){

        mPresenter.onMovieDataNotAvailable();
        verify(mView).notifyUserNoMovie();
    }

/*    @PrepareForTest({ MovieDBUtils.class })
    @Test
    public void startLoadingMovieDetails(){

        PowerMockito.mockStatic(MovieDBUtils.class);

        PowerMockito.when(MovieDBUtils.buildMoviePosterURL(anyString()))
                .thenReturn(any(URL.class));

        PowerMockito.when(MovieDBUtils.buildMovieBackdropURL(anyString()))
                .thenReturn(any(URL.class));

        mPresenter.start();

        verify(mView).disableTrailerFab();
        verify(mView).setActivityTitle("");
        verify(mView).setBannerText(anyString());
        verify(mView).setPosterImage(anyString(), any(ImageLoader.class));
        verify(mView).setBackdropImage(anyString(), any(ImageLoader.class));
        verify(mAppRepository).getTrailers(anyString(), any(MovieRemoteDataStore.GetTrailersCallback.class));

    }*/

    /**
     * The following methods are all blank since they are methods implemented
     * from callback interfaces that are passed into the AppRepository when making
     * calls to it. They do not need to be filled out since the SUT is the Presenter
     * class. The AppRepository will be tested in other classes to see if it is correctly
     * calling a callback.
     */

    @Override
    public void onMovieLoaded(Movie movie) {

    }

    @Override
    public void onMovieDataNotAvailable() {

    }

    @Override
    public void onTrailersLoaded(List<Trailer> trailerList) {

    }

    @Override
    public void onTrailerDataNotAvailable() {

    }

}
