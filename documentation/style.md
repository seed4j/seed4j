# Style

## Pattern Library

Component styles (CSS, HTML) are designed under `src/main/style` using [Tikui][tikui].

You can start Tikui with `npm run tikui:serve`, this will open the Pattern Library on [http://localhost:9005](http://localhost:9005).

To add a new component, you may like to install `tikui` command with `npm i -g @tikui/cli`.

Then create the component using the `seed4j` prefix, here is an example to create `my-atom` component.

```shell
tikui create -p seed4j my-atom src/main/style/atom
```

This will generate `my-atom` under atom directory, don't forget to edit the `atom.pug` and `_atom.scss` to show `my-atom` on the [atom](http://localhost:9005/atom/atom.html) page.

> You can do the same thing to make a `molecule`, an `organism` and a `template`.

## Glyph

If you need to add a new glyph icon, please notice we are using [Fontello][fontello]. Please read the [help][fontello-help] Fontello section for any questions.

To add a new glyph using Fontello, please launch:

```shell
npm run glyph:open
```

Then, when you've finished, please download the config only and replace it under `src/main/glyph/config.json`.

Now you're able to launch:

```shell
npm run glyph:build
```

This will replace the current _CSS_ and _font_ files into the sources.

[tikui]: https://www.tikui.org
[fontello]: https://fontello.com/
[fontello-help]: https://github.com/fontello/fontello/wiki/Help
