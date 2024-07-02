package one.nem.kidshift.utils.factory;

import dagger.assisted.AssistedFactory;
import one.nem.kidshift.utils.impl.KSLoggerImpl;

@AssistedFactory
public interface KSLoggerFactory {

    KSLoggerImpl create(String name);
}
