package com.allo.courtcounter.domain.base.interactor;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T, Params> {

    private final Scheduler threadExecutor;
    private final Scheduler threadPostExecution;
    private final CompositeDisposable disposables;

    public UseCase(Scheduler threadExecutor, Scheduler threadPostExecution) {
        this.threadExecutor = threadExecutor;
        this.threadPostExecution = threadPostExecution;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable<T> buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     *                 by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params   Parameters (Optional) used to build/execute this use case.
     */
    public void execute(DisposableObserver<T> observer, Params params) {
        if (observer == null) {
            throw new IllegalArgumentException("observer must not be null");
        }

        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(threadExecutor)
                .observeOn(threadPostExecution);
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        if (disposable == null) {
            throw new IllegalArgumentException("disposable must not be null");
        }

        disposables.add(disposable);
    }
}
