package com.alvinsvitzer.flixbook.moviedetail.pagerfragments;

import com.alvinsvitzer.flixbook.data.AppRepository;
import com.alvinsvitzer.flixbook.data.MovieDataStore;
import com.alvinsvitzer.flixbook.data.model.Movie;
import com.alvinsvitzer.flixbook.utilities.MovieDBUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Alvin on 2/22/17.
 */

@RunWith(PowerMockRunner.class)
public class MovieOverviewPresenterTest implements MovieDataStore.GetMovieCallback{

    private Movie mMovie;

    @Mock
    private AppRepository mAppRepository;

    @Mock
    private MovieOverviewContract.View mView;

    private MovieOverviewPresenter mPresenter;

    @Before
    public void setupObject() {

        MockitoAnnotations.initMocks(this);

        mPresenter = new MovieOverviewPresenter(mView, mAppRepository);

        mMovie = new Movie();
        mMovie.setMovieTitle("The Best Movie");
    }

    @Test
    public void verifyMovieDetailLoadOnStart(){

        mPresenter.start();

        verify(mAppRepository).getMovie(mPresenter);

    }

    @Test
    public void notifyUserMovieDataNotAvailable(){

        mPresenter.onMovieDataNotAvailable();
        verify(mView).notifyNoMovieData();

    }
    @Test
    public void detachViewSetsViewToNull(){

        mPresenter.detachView();
        assertNull(mPresenter.mView);

    }

    @PrepareForTest({ MovieDBUtils.class })
    @Test
    public void verifyMovieDataLoaded(){

        PowerMockito.mockStatic(MovieDBUtils.class);

        PowerMockito.when(MovieDBUtils.getLocalDate(anyString()))
                .thenReturn(anyString());

        mPresenter.onMovieLoaded(mMovie);

        verify(mView).setPlot(anyString());
        verify(mView).setReleaseDate(anyString());
        verify(mView).setVoteAverage(anyDouble());
    }

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

}