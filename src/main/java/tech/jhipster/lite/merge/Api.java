package tech.jhipster.lite.merge;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import tech.jhipster.lite.merge.impl.ApiImpl;
import tech.jhipster.lite.merge.impl.DeliverImpl;
import tech.jhipster.lite.merge.impl.Diamond;

/**
 * Diamond merge of generated file, custom file and base filer from earlier generations.
 * It is also called 'three-way merge'
 * <p>
 * The basis of the concept is that source generation is maintained isolated in '.genLog/..'
 * and custom-fitted files are located in the 'source' folders. In example 'src/main/java/..'
 * <p>
 * Api is an instance to a single diamond merge.
 * You need to use {@link #merge(String, String, String)} for each merge to be performed
 * <p>
 * The process is to:
 * <ol>
 *   <li>instantiate API using {@link #merge(String, String, String)}</li>
 *   <li>'verify' files. Using {@link Verify#verify()}</li>
 *   <li>check if merge is necessary? Place files where needed</li>
 *   <li>'merge' files. Using {@link Merge#merge()}</li>
 *   <li>check merge result and place files where needed.<br/>
 *       Method 'files' will help with that</li>
 * </ol>
 */
public interface Api {
  /**
   * Main entrance to merge
   *
   * @param base   how file was generated before this merge (if generated before)
   * @param gen    how the new generated file looks like now.
   * @param custom how the file looks in the source tree (if exists)
   */
  static ApiImpl merge(@Nullable String base, @NotNull String gen, @Nullable String custom) {
    final Diamond context = Diamond.of(base, gen, custom);
    return new ApiImpl(context);
  }

  DeliverImpl deliver();
}
