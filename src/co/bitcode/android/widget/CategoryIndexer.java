/*
 * Android Utilities
 * Copyright (C) 2010, 2011, 2012  Lorenzo Villani
 *
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package co.bitcode.android.widget;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import android.widget.SectionIndexer;

/**
 * Provides plumbing to easily implement a {@link SectionIndexer}. This class uses an enumeration to
 * determine the number of desired sections.
 * 
 * Enumeration values must be declared in ascending order.
 * 
 * The input is required to be sorted in ascending order, possibly employing the same criteria used
 * with the enumeration.
 * 
 * Undefined and unexpected behavior may occur if the rules above were not observed.
 * 
 * @param <E>
 *        Type of the enumeration.
 * @param <L>
 *        Type of list items associated with this indexer.
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class CategoryIndexer<E extends Enum<E>, L> implements SectionIndexer {
    private final Class<E> enumClass;
    private final EnumMap<E, Boolean> sectionFound;
    private final EnumMap<E, Integer> sectionToPosition;
    private final List<L> entries;
    private final List<E> positionToSection;

    /**
     * Constructor.
     * 
     * @param enumClass
     *        Class representing the enumeration.
     * @param entries
     *        Entries associated with this indexer. Used to compute section indexes.
     * @since 1.0.0
     */
    public CategoryIndexer(final Class<E> enumClass, final List<L> entries) {
        this.entries = entries;
        this.enumClass = enumClass;
        this.positionToSection = new ArrayList<E>(entries.size());
        this.sectionFound = new EnumMap<E, Boolean>(enumClass);
        this.sectionToPosition = new EnumMap<E, Integer>(enumClass);
    }

    /**
     * Callback method invoked for each element in the associated list when computing section
     * indexes.
     * 
     * @param entry
     *        Entry which is being inspected.
     * @return Category for current entry.
     * @since 1.0.0
     */
    public abstract E categorize(L entry);

    /**
     * Tells us to compute indexes for sections associated with this indexer.
     * 
     * @since 1.0.0
     */
    public void computeIndexes() {
        final int size = this.entries.size();

        resetCaches();

        for (int i = 0; i < size; i++) {
            final E category = categorize(this.entries.get(i));

            this.positionToSection.add(category);

            if (!getOptional(this.sectionFound, category, false)) {
                this.sectionToPosition.put(category, i);
                this.sectionFound.put(category, true);
            }
        }

        assert (this.sectionToPosition.size() == size);
    }

    /**
     * This is a reverse mapping to fetch the category value for a given position in the list.
     * 
     * @param position
     *        The position for which to return the section.
     * @return The section index. If the position is out of bounds, the section index must be
     *         clipped to fall within the size of the section array.
     */
    public E getCategoryForPosition(final int position) {
        return this.positionToSection.get(position);
    }

    /**
     * @param position
     *        The position for which to return the section.
     * @return <code>true</code> if position is the first in its section.
     * @since 1.0.0
     */
    public boolean isHeaderPosition(final int position) {
        for (final E category : this.sectionToPosition.keySet()) {
            if (position == getOptional(this.sectionToPosition, category, -1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Provides the starting index in the list for a given section.
     * 
     * @param category
     *        The category to jump to.
     * @return The starting position of that section. If the section is out of bounds, the position
     *         must be clipped to fall within the size of the list.
     * @since 1.0.0
     */
    public int getPositionForSection(final E category) {
        return getOptional(this.sectionToPosition, category, 0);
    }

    @Override
    public int getPositionForSection(final int section) {
        return getPositionForSection(getSections()[section]);
    }

    @Override
    public int getSectionForPosition(final int position) {
        return 0;
    }

    public Object[] getFoundSections() {
        return this.sectionFound.values().toArray();
    }

    @Override
    public E[] getSections() {
        return this.enumClass.getEnumConstants();
    }

    public int getSectionCount() {
        return getSections().length;
    }

    private void resetCaches() {
        this.positionToSection.clear();
        this.sectionFound.clear();
        this.sectionToPosition.clear();
    }

    private <K, D> D getOptional(final Map<K, D> map, final K key, final D defaultValue) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return defaultValue;
        }
    }
}
