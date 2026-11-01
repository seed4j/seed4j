export class LandscapeScroller {
  scroll(element: HTMLElement, scrollX: number, scrollY: number) {
    element.scroll(scrollX, scrollY);
  }

  scrollSmooth(element: HTMLElement, scrollX: number, scrollY: number) {
    element.scroll({ left: scrollX, top: scrollY, behavior: 'smooth' });
  }

  scrollIntoView(element: HTMLElement) {
    element.scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });
  }
}
