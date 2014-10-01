package org.apache.lucene.analysis.gosen;

/**
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.analysis.util.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.gosen.tokenAttributes.PartOfSpeechAttribute;
import org.apache.lucene.util.Version;

/**
 * Removes tokens that do NOT match a set of POS tags.
 * <p>
 * This is the inverse of {@link GosenPartOfSpeechStopFilter}.
 */
public final class GosenPartOfSpeechKeepFilter extends FilteringTokenFilter {
  private final Set<String> keepTags;
  private final PartOfSpeechAttribute posAtt = addAttribute(PartOfSpeechAttribute.class);

  /** @deprecated enablePositionIncrements=false is not supported anymore as of Lucene 4.4. */
  @Deprecated
  public GosenPartOfSpeechKeepFilter(Version version, boolean enablePositionIncrements, TokenStream input, Set<String> keepTags) {
    super(version, enablePositionIncrements, input);
    this.keepTags = keepTags;
  }

  public GosenPartOfSpeechKeepFilter(TokenStream input, Set<String> keepTags) {
    super(input);
    this.keepTags = keepTags;
  }

  @Override
  protected boolean accept() throws IOException {
    final String pos = posAtt.getPartOfSpeech();
    return pos != null && keepTags.contains(pos);
  }
}