"use strict";

function _typeof(obj) { if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

function _possibleConstructorReturn(self, call) { if (call && (_typeof(call) === "object" || typeof call === "function")) { return call; } return _assertThisInitialized(self); }

function _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); if (superClass) _setPrototypeOf(subClass, superClass); }

function _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }

var BaseXform = require('../base-xform');
/** https://en.wikipedia.org/wiki/Office_Open_XML_file_formats#DrawingML */


var EMU_PER_PIXEL_AT_96_DPI = 9525;

var ExtXform =
/*#__PURE__*/
function (_BaseXform) {
  _inherits(ExtXform, _BaseXform);

  function ExtXform(options) {
    var _this;

    _classCallCheck(this, ExtXform);

    _this = _possibleConstructorReturn(this, _getPrototypeOf(ExtXform).call(this));
    _this.tag = options.tag;
    _this.map = {};
    return _this;
  }

  _createClass(ExtXform, [{
    key: "render",
    value: function render(xmlStream, model) {
      xmlStream.openNode(this.tag);
      var width = Math.floor(model.width * EMU_PER_PIXEL_AT_96_DPI);
      var height = Math.floor(model.height * EMU_PER_PIXEL_AT_96_DPI);
      xmlStream.addAttribute('cx', width);
      xmlStream.addAttribute('cy', height);
      xmlStream.closeNode();
    }
  }, {
    key: "parseOpen",
    value: function parseOpen(node) {
      if (node.name === this.tag) {
        this.model = {
          width: parseInt(node.attributes.cx || '0', 10) / EMU_PER_PIXEL_AT_96_DPI,
          height: parseInt(node.attributes.cy || '0', 10) / EMU_PER_PIXEL_AT_96_DPI
        };
        return true;
      }

      return false;
    }
  }, {
    key: "parseText",
    value: function parseText()
    /* text */
    {}
  }, {
    key: "parseClose",
    value: function parseClose()
    /* name */
    {
      return false;
    }
  }]);

  return ExtXform;
}(BaseXform);

module.exports = ExtXform;
//# sourceMappingURL=ext-xform.js.map
