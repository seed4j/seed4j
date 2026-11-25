package com.seed4j.generator.server.springboot.banner;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.properties.Seed4JProjectBaseName;
import org.junit.jupiter.api.Test;

@UnitTest
class AsciiArtTest {

  @Test
  void shouldDrawSingleWord() {
    assertThat(AsciiArt.from(new Seed4JProjectBaseName("myapp")).text()).isEqualTo(
      """
      #   # #   #  ###  ####  ####
      ## ##  # #  #   # #   # #   #
      # # #   #   ##### ####  ####
      #   #   #   #   # #     #
      #   #   #   #   # #     #\
      """
    );
  }

  @Test
  void shouldSeparateWords() {
    assertThat(AsciiArt.from(new Seed4JProjectBaseName("myApp")).text()).isEqualTo(
      """
      #   # #   #        ###  ####  ####
      ## ##  # #        #   # #   # #   #
      # # #   #         ##### ####  ####
      #   #   #         #   # #     #
      #   #   #         #   # #     #\
      """
    );
  }

  @Test
  void shouldWrapWordsOnSeveralLines() {
    assertThat(AsciiArt.from(new Seed4JProjectBaseName("seed4jSampleApplication")).text()).isEqualTo(
      """
       #### ##### ##### ####  #   #   ###        ####  ###  #   # ####  #     #####
      #     #     #     #   # #   #     #       #     #   # ## ## #   # #     #
       ###  ###   ###   #   # #####     #        ###  ##### # # # ####  #     ###
          # #     #     #   #     # #   #           # #   # #   # #     #     #
      ####  ##### ##### ####      #  ###        ####  #   # #   # #     ##### #####

       ###  ####  ####  #      ###   ####  ###  #####  ###   ###  #   #
      #   # #   # #   # #       #   #     #   #   #     #   #   # ##  #
      ##### ####  ####  #       #   #     #####   #     #   #   # # # #
      #   # #     #     #       #   #     #   #   #     #   #   # #  ##
      #   # #     #     #####  ###   #### #   #   #    ###   ###  #   #\
      """
    );
  }

  @Test
  void shouldSplitTooLongWord() {
    assertThat(AsciiArt.from(new Seed4JProjectBaseName("abcdefghijklmnopqrstuvwxyz")).text()).isEqualTo(
      """
       ###  ####   #### ####  ##### #####  #### #   #  ###    ### #   # #     #   #
      #   # #   # #     #   # #     #     #     #   #   #       # #  #  #     ## ##
      ##### ####  #     #   # ###   ###   #  ## #####   #       # ###   #     # # #
      #   # #   # #     #   # #     #     #   # #   #   #   #   # #  #  #     #   #
      #   # ####   #### ####  ##### #      #### #   #  ###   ###  #   # ##### #   #

      #   #  ###  ####   ###  ####   #### ##### #   # #   # #   # #   # #   # #####
      ##  # #   # #   # #   # #   # #       #   #   # #   # #   #  # #   # #     #
      # # # #   # ####  # # # ####   ###    #   #   # #   # # # #   #     #     #
      #  ## #   # #     #  #  #  #      #   #   #   #  # #  ## ##  # #    #    #
      #   #  ###  #      ## # #   # ####    #    ###    #   #   # #   #   #   #####\
      """
    );
  }

  @Test
  void shouldDrawDigits() {
    assertThat(AsciiArt.from(new Seed4JProjectBaseName("0123456789")).text()).isEqualTo(
      """
       ###    #   ####  ####  #   # #####  ###  #####  ###   ###
      #  ##  ##       #     # #   # #     #        #  #   # #   #
      # # #   #    ###   ###  ##### ####  ####    #    ###   ####
      ##  #   #   #         #     #     # #   #  #    #   #     #
       ###   ###  ##### ####      # ####   ###  #      ###   ###\
      """
    );
  }
}
