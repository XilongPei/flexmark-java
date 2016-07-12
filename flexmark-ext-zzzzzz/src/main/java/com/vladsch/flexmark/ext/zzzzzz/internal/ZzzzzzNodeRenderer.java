package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.node.Document;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ZzzzzzNodeRenderer implements NodeRenderer
    , PhasedNodeRenderer // zzzoptionszzz(PHASED_NODE_RENDERER)
{
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    private final ZzzzzzOptions options;

    public ZzzzzzNodeRenderer(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(Zzzzzz.class, this::render),
                new NodeRenderingHandler<>(ZzzzzzBlock.class, this::render)
        ));
    }

    @Override//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    public Set<RenderingPhase> getRenderingPhases() {//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
        return new HashSet<>(Collections.singletonList(RenderingPhase.BODY_BOTTOM));//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    }//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    
    @Override//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    public void renderDocument(NodeRendererContext context, HtmlWriter html, Document document, RenderingPhase phase) {//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    }//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    
    private void render(Zzzzzz node, NodeRendererContext context, HtmlWriter html) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }

    private void render(ZzzzzzBlock node, NodeRendererContext context, HtmlWriter html) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }
}