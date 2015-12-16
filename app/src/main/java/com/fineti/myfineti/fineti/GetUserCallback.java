package com.fineti.myfineti.fineti;

/**
 * Created by sirin_000 on 27-Oct-15.
 */
interface GetUserCallback {

    /**
     * Invoked when background task is completed
     */
    void done(User returnedUser);

}