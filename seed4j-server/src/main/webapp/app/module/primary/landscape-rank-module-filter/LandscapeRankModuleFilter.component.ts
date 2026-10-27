import { ModuleRank, RANKS, extractRankLetter } from '@/module/domain/landscape/ModuleRank';
import type { ModuleRankStatistics } from '@/module/domain/ModuleRankStatistics';
import type { RankDescription } from '@/module/domain/RankDescription';
import { Optional } from '@/shared/optional/domain/Optional';
import { ToggleButtonExpandable } from '@/shared/toggle-button-expandable/infrastructure/primary';
import { PropType, defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'LandscapeRankModuleFilterVue',
  components: { ToggleButtonExpandable },
  props: {
    moduleRankStatistics: {
      type: Array as PropType<ModuleRankStatistics>,
      required: true,
    },
  },
  emits: ['selected'],
  setup(props, { emit }) {
    const ranks = RANKS;
    const selectedRank = ref<Optional<ModuleRank>>(Optional.empty());
    const rankDescriptions: RankDescription = {
      RANK_D: 'Experimental or advanced module requiring specific expertise',
      RANK_C: 'Module without known production usage',
      RANK_B: 'Module with at least one confirmed production usage',
      RANK_A: 'Module with multiple production usages across different projects and documented through talks, books or blog posts',
      RANK_S: 'Production-proven module providing unique features, validated by community feedback (10+ endorsements)',
    };

    const isRankSelected = (rank: ModuleRank): boolean => isSelectedRankEqualTo(rank);

    const isSelectedRankEqualTo = (rank: ModuleRank): boolean => selectedRank.value.filter(r => r === rank).isPresent();

    const toggleRank = (rank: ModuleRank): void => {
      selectedRank.value = isSelectedRankEqualTo(rank) ? Optional.empty() : Optional.ofNullable(rank);

      emit('selected', selectedRank.value);
    };

    const formatRankShortName = (rank: ModuleRank): string => rank.replace('RANK_', '');

    const formatRankFullName = (rank: ModuleRank): string => rank.replace('RANK_', 'RANK ');

    const getRankDescription = (rank: ModuleRank): string => rankDescriptions[rank];

    const isRankDisabled = (rank: ModuleRank): boolean => props.moduleRankStatistics.find(ru => ru.rank === rank)?.quantity === 0;

    const getRankClasses = (rank: ModuleRank): string[] => {
      const classes: string[] = [];

      if (selectedRank.value.isPresent()) {
        classes.push('-rank-color', `-${extractRankLetter(rank).toLowerCase()}`);

        if (selectedRank.value.filter(r => r !== rank).isPresent()) {
          classes.push('-reduced-attention');
        }
      }

      return classes;
    };

    return {
      ranks,
      isRankSelected,
      toggleRank,
      formatRankShortName,
      formatRankFullName,
      getRankDescription,
      isRankDisabled,
      getRankClasses,
    };
  },
});
