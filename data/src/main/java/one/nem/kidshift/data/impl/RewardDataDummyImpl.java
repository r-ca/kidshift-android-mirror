package one.nem.kidshift.data.impl;

import com.github.javafaker.Faker;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.utils.KSLogger;

public class RewardDataDummyImpl implements RewardData {

    private final Faker faker;

    @Inject
    KSLogger logger;

    @Inject
    public RewardDataDummyImpl() {
        faker = new Faker();
//        logger.setTag("RewardDataDummyImpl");

    }

    @Override
    public CompletableFuture<Integer> getTotalReward() {
        return CompletableFuture.supplyAsync(() -> faker.number().numberBetween(0, 1000));
    }
}
