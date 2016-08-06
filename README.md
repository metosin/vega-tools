# vega-tools

Utilities for working with [Vega][vega] visualization grammar in ClojureScript.

* Validating a Vega specification against Vega JSON-Schema.
* Main entry point of Vega runtime, `vg.parse.spec`, wrapped into [Promesa][promesa] promises.

[vega]: https://vega.github.io/vega/
[promesa]: https://github.com/funcool/promesa

## Latest version

    [metosin/vega-tools "0.1.0"]

## Example

Compare and contrast to [Vega Runtime documentation](https://github.com/vega/vega/wiki/Runtime):

```clj
(ns example
  (:require [vega-tools.core :refer [validate-and-parse]]
            [promesa.core :as p]))

(defn main []
 (let [spec {:width 200 :height 200
             :marks [{:type "symbol"
                      :properties {:enter {:size {:value 1000}
                                           :x {:value 100}
                                           :y {:value 100}
                                           :shape {:value "circle"}
                                           :stroke {:value "red"}}}}]}]
    (-> (validate-and-parse spec)
        (p/catch #(js/alert (str "Unable to parse spec:\n\n" %)))
        (p/then #(-> #js {:el (js/document.getElementById "#chart")}
                     (%)
                     (.update))))))
```

## License

### vega-schema.json

> Copyright (c) 2013, Trifacta Inc.
> Copyright (c) 2015, University of Washington Interactive Data Lab
> All rights reserved.
> 
> Redistribution and use in source and binary forms, with or without
> modification, are permitted provided that the following conditions are met:
> 
> 1. Redistributions of source code must retain the above copyright notice, this
>    list of conditions and the following disclaimer.
> 
> 2. Redistributions in binary form must reproduce the above copyright notice,
>    this list of conditions and the following disclaimer in the documentation
>    and/or other materials provided with the distribution.
> 
> 3. Neither the name of the copyright holder nor the names of its contributors
>   may be used to endorse or promote products derived from this software
>   without specific prior written permission.
> 
> THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
> AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
> IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
> DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
> FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
> DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
> SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
> CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
> OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
> OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


### vega-tools

Copyright © 2016 [Metosin Oy](http://www.metosin.fi/).

Distributed under the Eclipse Public License, the same as Clojure.
