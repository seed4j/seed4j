package tech.jhipster.lite.merge;

import java.util.List;
import tech.jhipster.lite.merge.impl.Body;
import tech.jhipster.lite.merge.impl.BodyBuilder;

/**
 * A role describes where a {@link Body} is used in three-way merge.
 */

public enum Role {
  /**
   * Starting leg in diamond merge. Both generated and custom are directly derived from this 'file'
   * 'base' is used on 'left' side in compare process.
   */
  base,
  /**
   * Generated 'file' from generator.
   * The file is compared to 'base' in the first 'diamond' merge step {@link BodyBuilder#apply(List)}.
   * As the 'right' side of compare.
   */
  gen,
  /**
   * Custom file. Located in sources. In example 'src/main/resources/config/application.properties'
   * The file is compared to 'base' in the second 'diamond' merge step {@link BodyBuilder#apply(List)}.
   * As the 'right' side of compare.
   */
  custom,
  /**
   * Output from diamond merge.
   * This is the diamond right side that is produced from 'base' + patches from 'gen' and patches from 'custom'
   */
  target,
}
