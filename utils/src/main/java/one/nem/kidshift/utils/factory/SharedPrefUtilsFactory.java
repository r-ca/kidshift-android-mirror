package one.nem.kidshift.utils.factory;

import dagger.assisted.AssistedFactory;
import one.nem.kidshift.utils.impl.SharedPrefUtilsImpl;

@AssistedFactory
public interface SharedPrefUtilsFactory {
    SharedPrefUtilsImpl create(String name);
}
