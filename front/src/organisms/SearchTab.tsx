import { ITab } from '../types/organisms/SearchTab.types';
import useSearchDataAtom from '../jotai/searchData';
// const TAB_TAG = 0;
// const TAB_REGION = 1;
// const TAB_ACCOUNT = 2;
const TAB_NAMES = ['태그', '지역', '계정'];

const Tab = (props: ITab) => {
  return (
    <button
      type="button"
      className={`${props.isSelected ? 'border-black' : 'border-white'} border-b-2 w-full`}
      onClick={props.onClick}
    >
      {TAB_NAMES[props.tabId]}
    </button>
  );
};

function SearchTab() {
  const [searchData, setSearchData] = useSearchDataAtom();
  return (
    <div className="font-bold text-xl flex justify-around bg-white leading-10">
      {TAB_NAMES.map((element, idx) => (
        <Tab
          isSelected={searchData.tab === idx}
          onClick={() => setSearchData({ tab: idx })}
          tabId={idx}
          key={TAB_NAMES[idx]}
        />
      ))}
    </div>
  );
}

export default SearchTab;
